from cscore import CameraServer
from networktables import NetworkTables
from networktables import NetworkTablesInstance
import cv2
import numpy as np
import time

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
#hsv_img = np.zeros(shape = (240, 320, 3), dtype = np.uint8)
#binary_img = np.zeros(shape = (240, 320, 3), dtype = np.uint8)
while True:
    time, input_img = sink.grabFrame(img)
    output_img = np.copy(input_img)
    
    if time == 0: # There is an error	
        outputSource.notifyError(sink.getError())
        continue

    hsv_img = cv2.cvtColor(input_img, cv2.COLOR_BGR2HSV)
    
    binary_img = cv2.inRange(hsv_img, (160, 100, 0), (180, 255, 255))
    kernel = np.ones((3,3), np.uint8)
    binary_img = cv2.morphologyEx(binary_img, cv2.MORPH_OPEN, kernel)
    #input_img = cv2.rectangle(binary_img, (0, 0), (100, 100), (255, 0, 0), 2)
    _, contours, _ = cv2.findContours(binary_img, cv2.RETR_EXTERNAL, cv2.APPROX_SIMPLE)
    outputSource.putFrame(binary_img)
