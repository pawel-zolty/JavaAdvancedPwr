// Save as "HelloJNI.cpp"
#include <jni.h>                   // JNI header provided by JDK
#include <iostream>                // C++ standard IO header
#include "lab05_pop_ArrayJNIApp.h" // Generated
using namespace std;

void sort(bool orderBooleanValue, double *arrayToSort, int arrayLength);
void sortAsc(double *arr, int size);
void sortDesc(double *arr, int size);
jdouble *getDoubleArray(JNIEnv *env, jobjectArray array, int arrayLength);
jobjectArray createJObjectDoubleArray(JNIEnv *env, jdouble *arrayToSort, int arrayLength);

// Implementation of the native method sayHello()
JNIEXPORT void JNICALL Java_lab05_1pop_ArrayJNIApp_sayHello(JNIEnv *env, jobject thisObj)
{
   cout << "Hello World from C++!" << endl;
   return;
}

JNIEXPORT jobjectArray JNICALL Java_lab05_1pop_ArrayJNIApp_sort01(JNIEnv *env, jobject thisObj, jobjectArray array, jobject order)
{
   //get Bool
   jclass classBoolean = env->FindClass("java/lang/Boolean");
   jmethodID booleanValueMID = env->GetMethodID(classBoolean, "booleanValue", "()Z");
   bool orderBooleanValue = (bool)env->CallBooleanMethod(order, booleanValueMID);

   jsize arrayLength = env->GetArrayLength(array);
   jdouble *arrayToSort = getDoubleArray(env, array, arrayLength);
   sort(orderBooleanValue, arrayToSort, arrayLength);

   return createJObjectDoubleArray(env, arrayToSort, arrayLength);
}

JNIEXPORT jobjectArray JNICALL Java_lab05_1pop_ArrayJNIApp_sort02(JNIEnv *env, jobject thisObj, jobjectArray array)
{
   //get Bool from this object
   jclass thisClass = env->GetObjectClass(thisObj);
   jfieldID orderField = env->GetFieldID(thisClass, "order", "Ljava/lang/Boolean;");   
   jobject order = env->GetObjectField(thisObj, orderField);
   
   return Java_lab05_1pop_ArrayJNIApp_sort01(env, thisObj, array, order);
}

jdouble *getDoubleArray(JNIEnv *env, jobjectArray array, int arrayLength)
{
   jclass classDouble = env->FindClass("java/lang/Double");
   jmethodID doubleValueMID = env->GetMethodID(classDouble, "doubleValue", "()D");

   jdouble *arrayToSort = new jdouble[arrayLength];
   for (int i = 0; i < arrayLength; i++)
   {
      jobject objDouble = env->GetObjectArrayElement(array, i);
      arrayToSort[i] = env->CallDoubleMethod(objDouble, doubleValueMID);
   }
   return arrayToSort;
}

jobjectArray createJObjectDoubleArray(JNIEnv *env, jdouble *arrayToSort, int arrayLength)
{
   jclass classDouble = env->FindClass("java/lang/Double");
   jmethodID doubleInitMID = env->GetMethodID(classDouble, "<init>", "(D)V");

   jobjectArray sortedArray = env->NewObjectArray(arrayLength, classDouble, NULL);
   for (int i = 0; i < arrayLength; i++)
   {

      jobject doubleObject = env->NewObject(classDouble, doubleInitMID, arrayToSort[i]);
      env->SetObjectArrayElement(sortedArray, i, doubleObject);
   }
   return sortedArray;
}

void sort(bool orderBooleanValue, double *arrayToSort, int arrayLength)
{
   if (orderBooleanValue)
   {
      sortAsc(arrayToSort, arrayLength);
   }
   else
   {
      sortDesc(arrayToSort, arrayLength);
   }
}

void sortAsc(double *arr, int size)
{
   for (int i = 0; i < size; i++)
   {
      for (int j = i + 1; j < size; j++)
      {
         if (arr[j] < arr[i])
         {
            double temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
         }
      }
   }
   cout << "Ascending" << endl;
}

void sortDesc(double *arr, int size)
{
   for (int i = 0; i < size; i++)
   {
      for (int j = i + 1; j < size; j++)
      {
         if (arr[j] > arr[i])
         {
            double temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
         }
      }
   }
   cout << "Descending" << endl;
}