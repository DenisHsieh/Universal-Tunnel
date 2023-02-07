# Universal Tunnel: Privacy Policy

Welcome to Universal Tunnel for Android!

This is an open source Android App developed by Denis Hsieh. The source code is availiable on Github under Mozilla Public License. This app is also availiable on Google Play.

As a professional Android developer, I take privacy/security seriously and carefully. There is no personal data abuse here. No personal data collection without any user's approvement.

## Explanation of permissions requested in this App

Thare are 5 permissions can be fund in [AndroidManifest.xml](https://github.com/DenisHsieh/Universal-Tunnel/blob/e58878f986d53dec64be619833681725c2a785da/app/src/main/AndroidManifest.xml) file:

### Network
purpose - Checking the network is availiable or not, there will be a web backend in the future. If no network availiable, App can just use default assets(images, JSON files...) locally.
* android.permission.INTERNET
* android.permission.ACCESS_NETWORK_STATE

### Storage
purpose - For [Glide image library](https://bumptech.github.io/glide/doc/download-setup.html) to create cache to boost image loading efficiency.
* android.permission.READ_EXTERNAL_STORAGE
* android.permission.WRITE_EXTERNAL_STORAGE

### Notification
purpose - For App's notification functionality to notify user the coming of new messages.
* android.permission.POST_NOTIFICATIONS

If there is any possible privacy/security issue, please let me know.


***Sincerely,***   
***Denis Hsieh***
