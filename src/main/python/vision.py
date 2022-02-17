from cscore import CameraServer
from networktables import NetworkTables
from networktables import NetworkTablesInstance
import cv2
import numpy as np
import time
import math

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
lower_bound = vision_nt.getNumberArray("HSVValuesLowerBound")
upper_bound = vision_nt.getNumberArray("HSVValuesUpperBound")
#hsv_img = np.zeros(shape = (240, 320, 3), dtype = np.uint8)
#binary_img = np.zeros(shape = (240, 320, 3), dtype = np.uint8)
while True:
    time, input_img = sink.grabFrame(img)
    output_img = np.copy(input_img)
    
    if time == 0: # There is an error	
        outputSource.notifyError(sink.getError())
        continue

    hsv_img = cv2.cvtColor(input_img, cv2.COLOR_BGR2HSV)
    
    binary_img = cv2.inRange(hsv_img, lower_bound, upper_bound)
    kernel = np.ones((3,3), np.uint8)
    binary_img = cv2.morphologyEx(binary_img, cv2.MORPH_OPEN, kernel)
    #input_img = cv2.rectangle(binary_img, (0, 0), (100, 100), (255, 0, 0), 2)
    _, contours, _ = cv2.findContours(binary_img, cv2.RETR_EXTERNAL, cv2.APPROX_SIMPLE)
    # Mandrews
    #  - done use contours to find ball center, 
    #  - done find relative position of ball center to camera center (based on resolution above)
    #  - done put counter, ball found flag, relative positions into the Vision network table
    #  - draw a cross on the ball found on the imput image and send that to the drive station (can be done later_)
    for contour in contours:

        if cv2.contourArea(contour) < 15:
            
            continue
        
        cv2.drawContours(binary_img, contour, -1, color = (255, 255, 255), thickness = -1)
        ball = cv2.minEnclosingCircle(contour)
        center, size = ball
        int(x), int(y) = center
        x_rel = 160 - x


        count = len(contours)
    outputSource.putFrame(binary_img) 
