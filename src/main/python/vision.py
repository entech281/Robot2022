from cscore import CameraServer
from networktables import NetworkTables
from networktables import NetworkTablesInstance
import cv2
import numpy as np
import time
import math

print("waiting 30 seconds")
time.sleep(30)
print("wait complete")

ntinst = NetworkTablesInstance.getDefault()
ntinst.startClientTeam(281)
ntinst.startDSClient()

vision_nt = NetworkTables.getTable('Vision')

time.sleep(0.5)

cs =  CameraServer.getInstance()
cs.enableLogging()

camera = cs.startAutomaticCapture()
camera.setResolution(320, 240)

sink = cs.getVideo()

outputSource = cs.putVideo("Feed", 320, 240)

img = np.zeros(shape = (240, 320, 3), dtype = np.uint8)
lower_bound = vision_nt.getNumberArray("HSVValuesLowerBound", (0,0,0))
upper_bound = vision_nt.getNumberArray("HSVValuesUpperBound", (0,0,0))

counter = 0
while True:
    counter += 1
    x_rel = 0
    y_rel = 0
    isBallFound = False
    time, input_img = sink.grabFrame(img)
    output_img = np.copy(input_img)

    lower_bound = vision_nt.getNumberArray("HSVValuesLowerBound", (0,0,0))
    upper_bound = vision_nt.getNumberArray("HSVValuesUpperBound", (0,0,0))
    if lower_bound[0] == 0:
        continue

    if time == 0: # There is an error
        outputSource.notifyError(sink.getError())
        continue

    blurred = cv2.GaussianBlur(input_img, (11,11), 0)
    hsv_img = cv2.cvtColor(blurred, cv2.COLOR_BGR2HSV)

    binary_img = cv2.inRange(hsv_img, lower_bound, upper_bound)
    binary_img = cv2.erode(binary_img, None, iterations=2)
    binary_img = cv2.dilate(binary_img, None, iterations=2)

    kernel = np.ones((3,3), np.uint8)
    binary_img = cv2.morphologyEx(binary_img, cv2.MORPH_OPEN, kernel)
    _, contours, _ = cv2.findContours(binary_img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    # Mandrews
    #  - draw a cross on the ball found on the imput image and send that to the drive station (can be done later)

    indx = -1
    indx_ratio = 100.0
    for i in range(len(contours)):
        contour = contours[i]
        if cv2.contourArea(contour) < 15:
            continue
        x,y,w,h = cv2.boundingRect(contour)
        if (w>2) and (h>2):
            ratio = float(max(w,h))/float(min(w,h))
            if ratio < indx_ratio:
                indx = i

    if indx > -1:
        contour = contours[indx]
        isBallFound = True
        cv2.drawContours(binary_img, contour, -1, color = (255, 255, 255), thickness = -1)
        ball = cv2.minEnclosingCircle(contour)
        center, size = ball
        x, y = center
        cv2.circle(output_img, (int(x), int(y)), int(size), (255,255,255), 2)
        x_rel = 160 - x #Positive -> left hand side
        y_rel = 120 - y #Positive -> top half of the screen

    vision_nt.putNumber("Counter", counter)
    vision_nt.putBoolean("isBallFound", isBallFound)
    vision_nt.putNumber("x", x_rel)
    vision_nt.putNumber("y", y_rel)
    vision_nt.putNumber("color hue avg",(lower_bound[0]+upper_bound[0])/2)
    outputSource.putFrame(output_img)
