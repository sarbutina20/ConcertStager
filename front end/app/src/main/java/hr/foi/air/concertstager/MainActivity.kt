package hr.foi.air.concertstager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hr.foi.air.concertstager.chat.roomdatabase.ChatDatabase
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.Roles.UserRole
import hr.foi.air.concertstager.pages.HomePage
import hr.foi.air.concertstager.core.login.LoginHandler
import hr.foi.air.concertstager.core.login.LoginUserData
import hr.foi.air.concertstager.login_google_login.GoogleLogin
import hr.foi.air.concertstager.manual_login.ManualLoginHandler
import hr.foi.air.concertstager.pages.ChatPage
import hr.foi.air.concertstager.pages.ChoosingRolePage
import hr.foi.air.concertstager.pages.ConcertDetailsPage
import hr.foi.air.concertstager.pages.EditProfilePage
import hr.foi.air.concertstager.pages.ListOfGroupsPage
import hr.foi.air.concertstager.ui.theme.ConcertStagerTheme
import hr.foi.air.concertstager.pages.LoginPage
import hr.foi.air.concertstager.pages.OrganizerEventPage
import hr.foi.air.concertstager.pages.OrganizerProfilePage
import hr.foi.air.concertstager.pages.PerformerProfilePage
import hr.foi.air.concertstager.pages.OrganizerRegistrationPage
import hr.foi.air.concertstager.pages.PerformerEventPage
import hr.foi.air.concertstager.pages.PerformerRegistrationPage
import hr.foi.air.concertstager.pages.VenueProfilePage
import hr.foi.air.concertstager.pages.VisitorHomePage
import hr.foi.air.concertstager.pages.VisitorProfilePage
import hr.foi.air.concertstager.pages.ManualChoosingRolePage
import hr.foi.air.concertstager.pages.NewEventPage
import hr.foi.air.concertstager.pages.OrganizerManualRegistrationPage
import hr.foi.air.concertstager.pages.PerformerManualRegistrationPage
import hr.foi.air.concertstager.pages.SuccessfulRegistrationPage
import hr.foi.air.concertstager.pages.VisitorManualRegistrationPage
import hr.foi.air.concertstager.viewmodels.Auth.GoogleLoginViewModel
import hr.foi.air.concertstager.viewmodels.Auth.ManualLoginViewModel

class MainActivity : ComponentActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private var loginHandlers = mutableListOf<LoginHandler>()
    private lateinit var googleLoginHandler : GoogleLogin
    private var manualLoginHandler: ManualLoginHandler = ManualLoginHandler()

    private lateinit var manualLoginViewModel: ManualLoginViewModel
    private lateinit var googleLoginViewModel: GoogleLoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        ChatDatabase.buildInstance(applicationContext)
        super.onCreate(savedInstanceState)
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result != null) {
                    Log.i("ActivityResult", "Result Code: ${result.resultCode}")
                    if (result.data != null) {
                        Log.i("ActivityResult", "Result Data: ${result.data}")
                        googleLoginHandler.handleSignInResult(result.data)
                    } else {
                        Log.i("ActivityResult", "Result Data is NULL")
                    }
                } else {
                    Log.i("ActivityResult", "Result is NULL")
                }
            }

        manualLoginViewModel = ViewModelProvider(this)[ManualLoginViewModel::class.java]
        googleLoginViewModel = ViewModelProvider(this)[GoogleLoginViewModel::class.java]

        googleLoginHandler = GoogleLogin(this, activityResultLauncher)
        loginHandlers.add(googleLoginHandler)
        loginHandlers.add(manualLoginHandler)


        setContent {
            ConcertStagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "login") {

                        composable("login") {
                            LoginPage(
                                onSuccessfulLogin = {
                                    val userData = UserLoginContext.loggedUser!!
                                    //if (userData.user_email == "sebastijan.bicak@gmail.com") navController.navigate("nesto")
                                    when (userData.user_roleId) {
                                        null -> navController.navigate("chooseRole")
                                        UserRole.Organizer.value -> navController.navigate("organizerEventsPage")
                                        UserRole.Performer.value -> navController.navigate("performerEventsPage")
                                        UserRole.Visitor.value -> navController.navigate("visitorHomePage")
                                    }
                                },
                                manualLoginViewModel = manualLoginViewModel,
                                googleLoginViewModel = googleLoginViewModel,
                                loginHandlers = loginHandlers,
                                onSignUpClick = {
                                    navController.navigate("chooseManualRole")
                                }
                            )
                        }
                        composable("chooseRole"){
                            ChoosingRolePage(
                                onSelectedRole = {
                                    val userData = UserLoginContext.loggedUser!!
                                    when (userData.user_roleId) {

                                        null -> navController.navigate("chooseRole")
                                        UserRole.Organizer.value -> navController.navigate("organizerRegistrationPage")
                                        UserRole.Performer.value -> navController.navigate("performerRegistrationPage")
                                        UserRole.Visitor.value -> navController.navigate("successRegistration")

                                    }
                                }
                            )
                        }
                        composable("chooseManualRole") {
                            ManualChoosingRolePage(
                                onSelectedRole = {
                                    val userData = UserLoginContext.loggedUser!!
                                    when (userData.user_roleId) {
                                        null -> navController.navigate("chooseManualRole")
                                        1 -> navController.navigate("organizerManualRegistrationPage")
                                        2 -> navController.navigate("performerManualRegistrationPage")
                                        3 -> navController.navigate("visitorManualRegistrationPage")
                                    }
                                },
                                returnToLoginPage = {
                                    navController.navigate("login")
                                })
                        }
                        composable("organizerRegistrationPage"){
                            OrganizerRegistrationPage(
                                returnToPreviousPage = {
                                    UserLoginContext.loggedUser?.user_contact_number = null
                                    navController.navigate("chooserole")
                                },
                                continueToNextPage = {
                                    navController.navigate("successRegistration")
                                }
                            )
                        }

                        composable("chatPage/{receiverName}"){ backStackEntry ->
                            val receiverName = backStackEntry.arguments?.getString("receiverName")
                            receiverName?.let {
                                ChatPage(
                                    receiverName = it,
                                    returnToListOfUsers = { navController.navigate("listOfGroupsPage") }
                                )
                            }
                        }
                        composable("performerRegistrationPage"){
                            PerformerRegistrationPage(

                                returnToPreviousPage = {
                                    UserLoginContext.loggedUser?.user_genreId = null
                                    navController.navigate("chooserole")
                                },
                                continueToNextPage = {
                                    navController.navigate("successRegistration")
                                }

                            )
                        }

                        composable("organizerManualRegistrationPage"){
                            OrganizerManualRegistrationPage(
                                returnToRoleChoosing = {
                                    navController.navigate("chooseManualRole")
                                },
                                onSuccessfulRegistration = {
                                    navController.navigate("successRegistration")
                                }
                            )
                        }
                        composable("performerManualRegistrationPage"){
                            PerformerManualRegistrationPage(
                                returnToRoleChoosing = {
                                    navController.navigate("chooseManualRole")
                                },
                                onSuccessfulRegistration = {
                                    navController.navigate("successRegistration")
                                }
                            )
                        }
                        composable("visitorManualRegistrationPage"){
                            VisitorManualRegistrationPage(
                                returnToRoleChoosing = {
                                    navController.navigate("chooseManualRole")
                                },
                                onSuccessfulRegistration = {
                                    navController.navigate("successRegistration")
                                }
                            )
                        }

                        composable("listOfGroupsPage"){
                            ListOfGroupsPage(
                                openChatPageWithUser = {receiverName ->
                                    navController.navigate("chatPage/$receiverName")
                                }
                            )
                        }

                        composable("successRegistration"){
                            val userData = UserLoginContext.loggedUser!!
                            if (userData.user_name != "" && userData.user_roleId != null){

                                /*SuccesfullRegistrationPage(
                                    userName = userData.user_name,
                                    role = userData.user_roleId,
                                    goToLoginPage = {
                                        navController.navigate("login")
                                    },
                                    returnToRoleChoosing = {
                                        navController.navigate("chooserole")

                                    }*/

                                SuccessfulRegistrationPage(
                                    name = userData.user_name,
                                    role = userData.user_roleId,
                                    goToHomePageClick = {
                                        when(userData.user_roleId){
                                            UserRole.Organizer.value -> navController.navigate("organizerEventsPage")
                                            UserRole.Performer.value -> navController.navigate("performerEventsPage")
                                            UserRole.Visitor.value -> navController.navigate("visitorHomePage")
                                        }
                                        //navController.navigate("homePage")
                                    },
                                    goBackToRegistration = { role ->
                                        if (userData.user_google_id == null){
                                            when (role) {
                                                UserRole.Performer.value -> navController.navigate("performerManualRegistrationPage")
                                                UserRole.Visitor.value -> navController.navigate("visitorManualRegistrationPage")
                                                UserRole.Organizer.value -> navController.navigate("organizerManualRegistrationPage")
                                            }
                                        }
                                        else {
                                            when (role) {
                                                UserRole.Organizer.value -> navController.navigate("organizerRegistrationPage")
                                                UserRole.Performer.value -> navController.navigate("performerRegistrationPage")
                                                UserRole.Visitor.value -> navController.navigate("chooserole")
                                            }
                                        }

                                    }
                                )
                            }
                        }

                        composable("visitorHomePage"){
                            VisitorHomePage(navController = navController

                            )
                        }

                        composable("performerEventsPage"){

                            PerformerEventPage(navController = navController

                            )
                        }
                        composable("organizerEventsPage"){

                            OrganizerEventPage(navController = navController

                            )
                        }

                        composable("homePage"){

                            val userData = UserLoginContext.loggedUser!!
                            HomePage(loggedUser = userData,
                                onProfileClick = {
                                    when(userData.user_roleId){
                                        UserRole.Performer.value -> {
                                            navController.navigate("performerProfile/${userData.userId}")
                                        }
                                        UserRole.Organizer.value -> {
                                            navController.navigate("organizerProfile/${userData.userId}")
                                        }
                                        UserRole.Visitor.value -> {
                                            navController.navigate("visitorProfile/${userData.userId}")
                                        }
                                    }
                                }
                            )
                        }

                        composable("concertDetailsPage/{concertId}"){backStackEntry ->
                            val concertId = backStackEntry.arguments?.getString("concertId")?.toIntOrNull()
                            concertId?.let {
                                ConcertDetailsPage(id = it, navController = navController)
                            }
                        }

                        composable("newEventPage"){
                            NewEventPage({navController.popBackStack()} ,{navController.navigate("organizerEventsPage")})
                        }


                        composable("editProfilePage"){
                            EditProfilePage(navController = navController)
                        }

                        composable("organizerProfile/{id}") { navBackStackEntry ->
                            val userId = navBackStackEntry.arguments?.getString("id")?.toIntOrNull()
                            userId?.let {
                                OrganizerProfilePage(id = it, navController = navController)
                            }
                        }

                        composable("venueProfile/{id}"){ navBackStackEntry ->
                            val venueId = navBackStackEntry.arguments?.getString("id")?.toIntOrNull()
                            venueId?.let {
                                VenueProfilePage(id = it, navController = navController)
                            }
                        }

                        composable("performerProfile/{id}"){ navBackStackEntry ->
                            val userId = navBackStackEntry.arguments?.getString("id")?.toIntOrNull()
                            userId?.let {
                                PerformerProfilePage(id = it, navController = navController)
                            }
                        }

                        composable("visitorProfile/{id}"){navBackStackEntry ->
                            val userId = navBackStackEntry.arguments?.getString("id")?.toIntOrNull()
                            userId?.let {
                                VisitorProfilePage(id = it, navController = navController)
                            }
                        }

                        composable("homePage"){
                            UserLoginContext.loggedUser = LoginUserData(
                                userId = 6,
                                user_google_id = "ossmszakgas",
                                user_name = "Liam",
                                user_email = "liam@gmail.com",
                                user_roleId = 3,
                                user_genreId = null,
                                user_contact_number = null,
                                jwt = "nesto21321"
                            )
                            val userData = UserLoginContext.loggedUser!!
                            HomePage(loggedUser = userData,
                                onProfileClick = {
                                    when(userData.user_roleId){
                                        UserRole.Performer.value -> {
                                            navController.navigate("performerProfile/${userData.userId}")
                                        }
                                        UserRole.Organizer.value -> {
                                            navController.navigate("organizerProfile/${userData.userId}")
                                        }
                                        UserRole.Visitor.value -> {
                                            navController.navigate("visitorProfile/${userData.userId}")
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}