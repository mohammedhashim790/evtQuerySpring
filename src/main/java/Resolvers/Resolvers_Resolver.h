/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class Resolvers_Resolver */

#ifndef _Included_Resolvers_Resolver
#define _Included_Resolvers_Resolver
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     Resolvers_Resolver
 * Method:    Add
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_Resolvers_Resolver_Add
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     Resolvers_Resolver
 * Method:    Query
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_Resolvers_Resolver_Query
  (JNIEnv *, jobject, jstring);

/*
 * Class:     Resolvers_Resolver
 * Method:    QueryObject
 * Signature: ()Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_Resolvers_Resolver_QueryObject
  (JNIEnv *, jobject);

/*
 * Class:     Resolvers_Resolver
 * Method:    QueryChannelsNext
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_Resolvers_Resolver_QueryChannelsNext
  (JNIEnv *, jobject, jstring, jint);

/*
 * Class:     Resolvers_Resolver
 * Method:    QueryChannelsPrev
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_Resolvers_Resolver_QueryChannelsPrev
  (JNIEnv *, jobject, jstring, jint);

/*
 * Class:     Resolvers_Resolver
 * Method:    GetTotalLogsFromChannel
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_Resolvers_Resolver_GetTotalLogsFromChannel
  (JNIEnv *, jobject, jstring);

/*
 * Class:     Resolvers_Resolver
 * Method:    GetEvents
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_Resolvers_Resolver_GetEvents
  (JNIEnv *, jobject, jstring);

/*
 * Class:     Resolvers_Resolver
 * Method:    FetchAllData
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_Resolvers_Resolver_FetchAllData
  (JNIEnv *, jobject, jstring);

#ifdef __cplusplus
}
#endif
#endif
