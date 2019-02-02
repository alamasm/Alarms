package Server.Responses

import Server.requests.RequestTypes
import android.app.DownloadManager
import com.example.egor.alarms.Model.Server.Responses.Response

data class LoginResponse(override val responseType: Int = RequestTypes.LOGIN,
                         val logged: Boolean, val userId: Int
) : ResponseInterface