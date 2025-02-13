import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.same.alarm.setup.SetupScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupRoute(
) {
    SetupScreen()
}