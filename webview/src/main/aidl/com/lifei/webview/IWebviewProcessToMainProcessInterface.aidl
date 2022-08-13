// IWebviewProcessToMainProcessInterface.aidl
package com.lifei.webview;

// Declare any non-default types here with import statements

interface IWebviewProcessToMainProcessInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void handleWebCommand(String commandName, String jsonParams);
}
