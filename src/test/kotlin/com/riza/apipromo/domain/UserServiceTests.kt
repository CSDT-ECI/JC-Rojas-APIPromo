package com.riza.apipromo.domain

import com.riza.apipromo.domain.user.User
import com.riza.apipromo.domain.user.UserRepository
import com.riza.apipromo.domain.user.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class UserServiceTests {
    @Mock
    lateinit var userRepository: UserRepository

    private lateinit var userService: UserService

    @BeforeEach
    fun setupService() {
        userService = UserService(userRepository)
    }

    @Test
    fun saveUserShouldCallRepositoryAndReturnSavedUser() {
        val testUser = User(name = "Test User", promos = mutableSetOf())
        val expectedUser = testUser.copy(id = 1)

        `when`(userRepository.save(testUser)).thenReturn(expectedUser)

        Assertions.assertEquals(userService.save(testUser), expectedUser)
    }

    @Test
    fun findAllShouldCallRepositoryAndReturnAListWithAllUsers() {
        val expectedUser1 = User(id = 1, name = "Test User 1", promos = mutableSetOf())
        val expectedUser2 = User(id = 2, name = "Test User 2", promos = mutableSetOf())

        val expectedUsers = listOf(expectedUser1, expectedUser2)

        `when`(userRepository.findAll()).thenReturn(expectedUsers)

        Assertions.assertEquals(userService.findAll(), expectedUsers)
    }

    @Test
    fun findByIdShouldCallRepositoryAndReturnUser() {
        val expectedUser = User(id = 1, name = "Test User", promos = mutableSetOf())

        `when`(userRepository.findById(1)).thenReturn(expectedUser)

        Assertions.assertEquals(userService.findById(1), expectedUser)
    }

    @Test
    fun editUserFcmIdShouldUpdateUserFcmId() {
        val testUser = User(id = 1, name = "Test User", fcmId = "oldFcmId", promos = mutableSetOf())
        val expectedUser = testUser.copy(fcmId = "newFcmId")

        `when`(userRepository.findById(1)).thenReturn(testUser)
        `when`(userRepository.save(any<User>())).thenReturn(expectedUser)

        val result = userService.editUserFcmId(1, "newFcmId")

        Assertions.assertEquals(result?.fcmId, "newFcmId")
        verify(userRepository, times(1)).save(any<User>())
    }

    @Test
    fun deleteAllShouldCallRepository() {
        userService.deleteAll()
        verify(userRepository).deleteAll()
    }

    @Test
    fun findByIdShouldReturnNullWhenUserDoesNotExist() {
        `when`(userRepository.findById(999)).thenReturn(null)
        val result = userService.findById(999)
        Assertions.assertNull(result)
    }

    @Test
    fun editUserFcmIdShouldReturnNullWhenUserDoesNotExist() {
        `when`(userRepository.findById(999)).thenReturn(null)
        val result = userService.editUserFcmId(999, "newFcmId")
        Assertions.assertNull(result)
    }
}
