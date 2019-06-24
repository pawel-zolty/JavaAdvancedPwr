var fun1 = function (name) {
  print('Hi there from Javascript, ' + name);
  return "greetings from javascript";
};
//http://www.permadi.com/tutorial/jsCanvasGrayscale/index.html
var grayscale = function (imageData, imgWidth, imgHeight) {
  for (j = 0; j < imgHeight; j++) {
    for (i = 0; i < imgWidth; i++) {
      var index = (i * 3) * imgWidth + (j * 3);
      //print(index + " " + j + " " + i);
      
      var red = imageData[index];
      var green = imageData[index + 1];
      var blue = imageData[index + 2];

      var average = (red + green + blue) / 3;

      imageData[index] = average;
      imageData[index + 1] = average;
      imageData[index + 2] = average;
    }
  }
  return imageData;
}