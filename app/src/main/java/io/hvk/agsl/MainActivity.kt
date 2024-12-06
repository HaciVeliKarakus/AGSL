package io.hvk.agsl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import io.hvk.agsl.screens.HomeScreen
import io.hvk.agsl.ui.theme.AGSLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            val screens = listOf(
//                Screen.Home,
//                Screen.Create,
//                Screen.Settings
//            )
            AGSLTheme {
//                var selectedScreen by remember { mutableIntStateOf(0) }
//
//                Scaffold(
//                    modifier = Modifier.fillMaxSize(),
//                    bottomBar = {
//                        Row(
//                            verticalAlignment = Alignment.Bottom
//                        ) {
//                            Box(
//                                Modifier
//                                    .shadow(5.dp)
//                                    .background(color = MaterialTheme.colorScheme.surface)
//                                    .height(64.dp)
//                                    .fillMaxWidth()
//                                    .padding(horizontal = 16.dp)
//                            ) {
//                                Row(
//                                    Modifier.fillMaxWidth(),
//                                    verticalAlignment = Alignment.CenterVertically,
//                                ) {
//                                    for (screen in screens) {
//                                        val isSelected = screen == screens[selectedScreen]
//                                        val animatedWeight by animateFloatAsState(
//                                            targetValue = if (isSelected) 1.5f else 1f, label = ""
//                                        )
//                                        Box(
//                                            modifier = Modifier.weight(animatedWeight),
//                                            contentAlignment = Alignment.TopCenter,
//                                        ) {
//                                            val interactionSource =
//                                                remember { MutableInteractionSource() }
//                                            BottomNavItem(
//                                                modifier = Modifier.clickable(
//                                                    interactionSource = interactionSource,
//                                                    indication = null
//                                                ) {
//                                                    selectedScreen = screens.indexOf(screen)
//                                                },
//                                                screen = screen,
//                                                isSelected = isSelected
//                                            )
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                ) { innerPadding ->
//
//
//                    Column(modifier = Modifier.padding(innerPadding)) {
//                       AnimatedContent(selectedScreen, label = "") { currentScreen ->
//                           when (currentScreen) {
//                               0 -> {
//                                   HomeScreen()
//                               }
//                               1 -> {
//                                   ProfileScreen()
//                               }
//                               2 -> {
//                                   SettingScreen()
//                               }
//                           }
//                       }
//                    }
//                }

                BottomSheetNavigator {
                    Navigator(HomeScreen) {
                        SlideTransition(it)
                    }
                }
            }
        }
    }
}


//@Composable
//fun MainScreen() {
//    val navController = rememberNavController()
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//
//    // Determine if we should show the bottom bar
//    val shouldShowBottomBar = when (navBackStackEntry?.destination?.route) {
//        Screen.Home.route, Screen.Quiz.route -> true
//        else -> false
//    }
//
//    Scaffold(
//        bottomBar = {
//            AnimatedVisibility(
//                visible = shouldShowBottomBar,
//                enter = slideInVertically(initialOffsetY = { it }),
//                exit = slideOutVertically(targetOffsetY = { it })
//            ) {
//                NavigationBar {
//                    val currentRoute = navBackStackEntry?.destination?.route
//
//                    // Home tab
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
//                        label = { Text("Learn") },
//                        selected = currentRoute == Screen.Home.route,
//                        onClick = {
//                            navController.navigate(Screen.Home.route) {
//                                popUpTo(navController.graph.startDestinationId)
//                                launchSingleTop = true
//                            }
//                        }
//                    )
//
//                    // Quiz tab
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Default.Quiz, contentDescription = "Quiz") },
//                        label = { Text("Quiz") },
//                        selected = currentRoute == Screen.Quiz.route,
//                        onClick = {
//                            navController.navigate(Screen.Quiz.route) {
//                                popUpTo(navController.graph.startDestinationId)
//                                launchSingleTop = true
//                            }
//                        }
//                    )
//                }
//            }
//        }
//    ) { paddingValues ->
//        NavHost(
//            navController = navController,
//            startDestination = Screen.Home.route,
//            modifier = Modifier.padding(
//                start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
//                end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
//                top = paddingValues.calculateTopPadding(),
//                bottom = if (shouldShowBottomBar) paddingValues.calculateBottomPadding() else 0.dp
//            )
//        ) {
//            composable(Screen.Home.route) {
//                HomeScreen(
//                    onLectureClick = {
////                        navController.navigate(Screen.LectureDetail.createRoute(lectureId))
//                    }
//                )
//            }
//            composable(Screen.Quiz.route) {
////                QuizScreen(
////                    quizViewModel = viewModel(),
////                    onCategoryClick = { categoryId ->
////                        navController.navigate(Screen.QuizCategory.createRoute(categoryId))
////                    }
////                )
//            }
//            composable(
//                Screen.LectureDetail.route,
//                arguments = listOf(navArgument("lectureId") { type = NavType.StringType })
//            ) { backStackEntry ->
////                LectureDetailScreen(
////                    lectureId = backStackEntry.arguments?.getString("lectureId"),
////                    onBackClick = { navController.popBackStack() }
////                )
//            }
//            composable(
//                Screen.QuizCategory.route,
//                arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
//            ) { backStackEntry ->
////                QuizCategoryScreen(
////                    categoryId = backStackEntry.arguments?.getString("categoryId"),
////                    quizViewModel = viewModel(),
////                    onBackClick = { navController.popBackStack() }
////                )
//            }
//        }
//    }
//}
//
