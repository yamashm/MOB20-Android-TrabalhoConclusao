package br.com.fiap.mob20_android_trabalhoconclusao.presentation.fcm

import android.content.Intent
import android.net.Uri
import android.util.Log
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.main.MainActivity
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.utils.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class mob20_android_trabalhoconclusaoFCMService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {

//        val isDeeplink = p0.data.containsKey("deeplink")

//        val intent =
//            if (isDeeplink) Intent(Intent.ACTION_VIEW, Uri.parse(p0.data["deeplink"])) else Intent(
//                this,
//                MainActivity::class.java
//            )
        val intent = Intent(this, MainActivity::class.java)

        val title = p0.data["title"] ?: p0.notification?.title ?: this.getString(R.string.app_name)
        val message = p0.data["message"] ?: p0.notification?.body ?: ""

        val channelID = this.getString(R.string.default_notification_channel_id)
        val channelName = this.getString(R.string.default_notification_channel_name)
        val channelDescription = this.getString(R.string.default_notification_channel_description)

        val notifChannel =
            NotificationUtils.NotifChannel(channelID, channelName, channelDescription)

        NotificationUtils.notificationSimple(this, title, message, notifChannel, intent)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.i("TOKEN_FIREBASE", p0)
    }
}