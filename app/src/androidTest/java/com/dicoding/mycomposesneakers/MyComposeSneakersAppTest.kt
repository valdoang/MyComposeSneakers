package com.dicoding.mycomposesneakers

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.mycomposesneakers.model.FakeSneakersDataSource
import com.dicoding.mycomposesneakers.ui.navigation.Screen
import com.dicoding.mycomposesneakers.ui.theme.MyComposeSneakersTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MyComposeSneakersAppTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyComposeSneakersTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                MyComposeSneakersApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("SneakersList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeSneakersDataSource.dummySneakers[10].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailSneakers.route)
        composeTestRule.onNodeWithText(FakeSneakersDataSource.dummySneakers[10].title).assertIsDisplayed()
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithText(FakeSneakersDataSource.dummySneakers[1].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailSneakers.route)
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_checkout_rightBackStack() {
        composeTestRule.onNodeWithText(FakeSneakersDataSource.dummySneakers[1].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailSneakers.route)
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithContentDescription("Home").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithContentDescription("Cart").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithContentDescription("About").performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithContentDescription("Home").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}