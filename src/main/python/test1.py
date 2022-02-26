
import cv2

image = cv2.imread('Screenshot_20220226_120445.png')

B, G, R = cv2.split(image)
# Corresponding channels are seperated

cv2.imshow("original", image)
cv2.waitKey(0)

hsv_img = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
cv2.imshow("hsv",hsv_img)
cv2.waitKey(0)

H, S, V = cv2.split(hsv_img)

cv2.imshow("hue", H)
cv2.waitKey(0)

cv2.imshow("sat", S)
cv2.waitKey(0)

cv2.imshow("value", V)
cv2.waitKey(0)

cv2.imshow("blue", B)
cv2.waitKey(0)

cv2.imshow("Green", G)
cv2.waitKey(0)

cv2.imshow("red", R)
cv2.waitKey(0)

cv2.destroyAllWindows()
