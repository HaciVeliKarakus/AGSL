package io.hvk.agsl.navigation

//sealed class Screen(val route: String) {
//    data object Home : Screen("home")
//    data object Quiz : Screen("quiz")
//    data object Settings : Screen("settings")
//
//    // Quiz category screen
//    data object QuizCategory : Screen("quiz/{categoryId}") {
//        fun createRoute(categoryId: String) = "quiz/$categoryId"
//    }
//    data object LectureDetail : Screen("lecture/{lectureId}") {
//        fun createRoute(lectureId: String) = "lecture/$lectureId"
//    }
//}