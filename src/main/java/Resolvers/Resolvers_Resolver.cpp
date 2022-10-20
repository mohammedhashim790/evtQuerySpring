#include<jni.h>
#include<iostream>
#include "Resolvers_Resolver.h"
#include <windows.h>
#include <sddl.h>
#include <stdio.h>
#include <winevt.h>


using namespace std;

#pragma comment(lib, "wevtapi.lib")

const int SIZE_DATA = 4096;
TCHAR XMLDataCurrent[SIZE_DATA];
TCHAR XMLDataUser[SIZE_DATA];

#define ARRAY_SIZE 2
#define TIMEOUT 1000  // 1 second; Set and use in place of INFINITE in EvtNext call

DWORD PrintResults(EVT_HANDLE hResults);
DWORD PrintEvent(EVT_HANDLE hEvent); // Shown in the Rendering Events topic
string PrettyPrint(EVT_HANDLE hResults, EVT_HANDLE hProviderMetadata);

string QueryChannel();
void ListChannels();
void GetPublisherName();

LPWSTR GetMessageString(
	EVT_HANDLE hMetadata,
	EVT_HANDLE hEvent,
	EVT_FORMAT_MESSAGE_FLAGS FormatId,
	DWORD* dwBufferUsed
);


JNIEXPORT jint JNICALL JNICALL Java_Resolvers_Resolver_Add(JNIEnv* env, jobject thisObj, jint num1, jint num2) {
	cout << "Numbers are : " << num1 << " & " << num2;

	cout << "Sum is : " << (num1 + num2);

	return (num1 + num2);
}

JNIEXPORT jstring JNICALL Java_Resolvers_Resolver_Query(JNIEnv* env, jobject thisObj) {
	return env->NewStringUTF(QueryChannel().c_str());
}



string QueryChannel() {
	WCHAR eventId[] = L"Event/System";
	//WCHAR eventId[] = L"Event/System[EventRecordID=2784864]";
	WCHAR path[] = L"Security";

	DWORD status = ERROR_SUCCESS;
	EVT_HANDLE hResults = NULL;
	LPWSTR pwsPath = path;
	LPWSTR pwsQuery = eventId;

	LPWSTR pwsMessage = NULL;


	EVT_HANDLE hProviderMetadata = NULL;

	LPWSTR pwszPublisherName = path;

	cout << "Result Started" << endl;

	hProviderMetadata = EvtOpenPublisherMetadata(NULL, pwszPublisherName, NULL, 0, 0);
	if (NULL == hProviderMetadata)
	{
		wprintf(L"EvtOpenPublisherMetadata failed with %d\n", GetLastError());
		goto cleanup;
	}


	hResults = EvtQuery(NULL, pwsPath, pwsQuery, EvtQueryChannelPath);// EvtQueryReverseDirection);
	if (NULL == hResults)
	{
		status = GetLastError();

		if (ERROR_EVT_CHANNEL_NOT_FOUND == status)
			wprintf(L"The channel was not found.\n");
		else if (ERROR_EVT_INVALID_QUERY == status)
			// You can call the EvtGetExtendedStatus function to try to get 
			// additional information as to what is wrong with the query.
			wprintf(L"The query is not valid.\n");
		else
			wprintf(L"EvtQuery failed with %lu.\n", status);

		goto cleanup;
	}
	Sleep(1000);

cleanup:
	/*PrintResults(hResults);
	cout << "Eng Of Print Result" << endl;*/

	string res = PrettyPrint(hResults, hProviderMetadata);


	return res;

	/*wprintf(L"Event message string: %s\n\n", pwsMessage);

	free(pwsMessage);
	pwsMessage = NULL;*/
}


string PrettyPrint(EVT_HANDLE hResults, EVT_HANDLE hProviderMetadata) {

	EVT_HANDLE hEvent[ARRAY_SIZE];
	DWORD status = ERROR_SUCCESS;
	DWORD dwReturned = 0;

	DWORD currentDwBufferUsed = 0;


	DWORD dwBufferUsed = 0;



	LPWSTR pwsMessage = NULL;


	//Result to Be returned as string
	//LPWSTR res = NULL;


	wstring wres;

	string res;


	if (!EvtNext(hResults, ARRAY_SIZE, hEvent, INFINITE, 0, &dwReturned))
	{
		wprintf(L"EvtNext failed with %lu\n", status);
		goto cleanup;
	}


	cout << "Pretty Print " << dwReturned << endl;



	for (int iter = 0; iter < dwReturned; iter++) {
		pwsMessage = GetMessageString(
			hProviderMetadata,
			hEvent[iter],
			EvtFormatMessageXml,
			&dwBufferUsed
		);

		currentDwBufferUsed += dwBufferUsed;

		cout << "Current BUffer : " << currentDwBufferUsed << endl;
		cout << "Result Buffer : " << dwBufferUsed << endl;

		char buffer[1000];

		wres.append(pwsMessage);


		dwBufferUsed = 0;

		/*wprintf(L"Event ID %d\nResult : \n%s \n", iter, pwsMessage);

		if (pwsMessage) {
			free(pwsMessage);
			pwsMessage = NULL;
		}*/


	}



	/*pwsMessage = GetMessageString(
		hProviderMetadata,
		hEvent,
		EvtFormatMessageXml
	);*/


	res = string(wres.begin(), wres.end());

	if (pwsMessage)
	{
		cout << endl << "End res : " << res << endl;
		return res;
	}


	//cout << "REs : " << endl << pwsMessage;




cleanup:

	if (hEvent)
		EvtClose(hEvent);

	if (hResults)
		EvtClose(hResults);

	if (hProviderMetadata)
		EvtClose(hProviderMetadata);


	return NULL;


}




LPWSTR GetMessageString(
	EVT_HANDLE hMetadata,
	EVT_HANDLE hEvent,
	EVT_FORMAT_MESSAGE_FLAGS FormatId,
	DWORD* dwBufferUsed
)
{
	LPWSTR pBuffer = NULL;
	DWORD dwBufferSize = 0;
	*dwBufferUsed = 0;

	DWORD status = 0;

	if (!EvtFormatMessage(hMetadata, hEvent, 0, 0, NULL, FormatId, dwBufferSize, pBuffer, dwBufferUsed))
	{
		status = GetLastError();
		if (ERROR_INSUFFICIENT_BUFFER == status)
		{
			if ((EvtFormatMessageKeyword == FormatId))
				pBuffer[*dwBufferUsed - 1] = L'\0';
			else
				dwBufferSize = *dwBufferUsed;

			pBuffer = (LPWSTR)malloc(dwBufferSize * sizeof(WCHAR));

			if (pBuffer)
			{
				EvtFormatMessage(hMetadata, hEvent, 0, 0, NULL, FormatId, dwBufferSize, pBuffer, dwBufferUsed);

				// Add the second null terminator character.
				if ((EvtFormatMessageKeyword == FormatId))
					pBuffer[*dwBufferUsed - 1] = L'\0';
			}
			else
			{
				wprintf(L"malloc failed\n");
			}
		}
		else if (ERROR_EVT_MESSAGE_NOT_FOUND == status || ERROR_EVT_MESSAGE_ID_NOT_FOUND == status)
			;
		else
		{
			wprintf(L"EvtFormatMessage failed with %u\n", status);
		}
	}

	return pBuffer;
}





