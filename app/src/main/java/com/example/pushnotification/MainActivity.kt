package com.example.pushnotification

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.pushnotification.ui.theme.PushNotificationTheme
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            PushNotificationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HyperLinkText()
                }
            }
        }
    }
}

@Composable
fun HyperLinkText() {
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        val str = "hello https://www.geeksforgeeks.org/add-hyperlink-at-a-particular-text-span-in-android-using-jetpack-compose/ welcome"
        val startIndex = str.indexOf("https")
        val substringToFind = "/"
        val endIndex = startIndex + substringToFind.length
        append(str)
        addStyle(
            style = SpanStyle(
                color = Color(0xff64B5F6),
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline
            ),
            start = startIndex,
            end = endIndex
        )

        // attach a string annotation that stores a URL to the text "link"
        addStringAnnotation(
            tag = "URL",
            annotation = "https://www.geeksforgeeks.org/add-hyperlink-at-a-particular-text-span-in-android-using-jetpack-compose/",
            start = startIndex,
            end = endIndex
        )
    }

// UriHandler parse and opens URI inside AnnotatedString Item in Browse
    val uriHandler = LocalUriHandler.current

// 🔥 Clickable text returns position of text that is clicked in onClick callback
    ClickableText(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        text = annotatedLinkString,
        onClick = {
            annotatedLinkString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}

@Preview
@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)

        ) {
            Image(
                painter = painterResource(id = com.example.pushnotification.R.drawable.group_453),
                contentDescription = "",
                modifier = Modifier
                    .width(230.dp)
                    .height(306.dp)
            )
            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
            Text(
                text = "Device access required",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(
                modifier = Modifier
                    .height(18.dp)
            )
            Text(
                text = "Please grant access to your camera, microphone, and Bluetooth before joining the class.",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.padding(bottom = 30.dp)
        ) {
            Text(
                text = "Depending on your sound device, Bluetooth access might be required. You can also turn off your camera and microphone any time during the class.",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            Button(
                onClick = {
                },
                modifier = Modifier
                    .width(360.dp)
                    .height(48.dp)
                    .padding(horizontal = 10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text(
                    text = "Allow Access",
                    color = Color.White
                )
            }
        }
    }
}

fun notifition(context: Context) {
    val builder = NotificationCompat.Builder(context, "CHANNEL_ID").apply {
        setContentTitle("Picture Download")
        setContentText("Download in progress")
        setPriority(NotificationCompat.PRIORITY_LOW)
    }
    val PROGRESS_MAX = 100
    val PROGRESS_CURRENT = 0
    NotificationManagerCompat.from(context).apply {
        // Issue the initial notification with zero progress
        builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
        val notificationId = 12345
        notify(notificationId, builder.build())

        // Do the job here that tracks the progress.
        // Usually, this should be in a
        // worker thread
        // To show progress, update PROGRESS_CURRENT and update the notification with:
        // builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        // notificationManager.notify(notificationId, builder.build());

        // When done, update the notification one more time to remove the progress bar
        builder.setContentText("Download complete")
            .setProgress(0, 0, false)
        notify(notificationId, builder.build())
    }
}

private fun createNotificationChannel(context: Context) {
    val CHANNEL_ID = "Cakap"
    var builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("Sourav")
        .setContentText("Android developer")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
}

//
// private fun createNotificationChannelMain() {
//    val CHANNEL_ID = "Cakap"
//    var context = LocalContext
//    // Create the NotificationChannel, but only on API 26+ because
//    // the NotificationChannel class is new and not in the support library
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val name = "Spoos"
//        val descriptionText = "andorid dev"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//        // Register the channel with the system
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }
// }

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun checkNotificationPolicyAccess(
    context: Context
) {
    val CHANNEL_ID = "Cakap_notification"
    var builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("My notification")
        .setContentText("Much longer text that cannot fit one line...")
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line...")
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PermissionDialog(context: Context) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = true
            ),
            title = { Text("Permission Needed") },
            text = {
                Text("Allow ******* to access Do Not Disturb Settings? Pressing 'No' will close the app as it cannot work without access. Thanks.")
            },
            onDismissRequest = { openDialog.value = true },

            dismissButton = {
                Button(
                    onClick = {
                        exitProcess(0)
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xffFF9800))
                ) {
                    Text(text = "No")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                        startActivity(context, intent, null)
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xffFF9800))
                ) {
                    Text(text = "Yes")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PushNotificationTheme {
        Greeting("Android")
    }
}
