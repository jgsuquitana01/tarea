package com.example.test.service

import com.example.test.model.Conference
import com.example.test.model.Register
import com.example.test.model.Member
import com.example.test.repository.ConferenceRepository
import com.example.test.repository.RegisterRepository
import com.example.test.repository.MemberRepository
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File


@SpringBootTest
class RegisterServiceTest {

  @InjectMocks
  lateinit var registerService: RegisterService

  @Mock
  lateinit var  registerRepository: RegisterRepository

  @Mock
  lateinit var memberRepository: MemberRepository

  @Mock
  lateinit var conferenceRepository: ConferenceRepository

  val jsonString = File("./src/test/resources/register.json").readText(Charsets.UTF_8)
  val registerMock = Gson().fromJson(jsonString, Register::class.java)

  var memberMock = Member().apply{
    id=1
    fullname="David Bermeo"
    email="dobermeo@sudamericano.edu.ec"
    age=21
  }

  val jsonStringSec = File("./src/test/resources/conference.json").readText(Charsets.UTF_8)
  val conferenceMock = Gson().fromJson(jsonStringSec, Conference::class.java)

  @Test
  fun saveRegisterWhenIsCorrect(){
    Mockito.`when`(memberRepository.findById(registerMock.memberId)).thenReturn(memberMock)
    Mockito.`when`(registerRepository.save(Mockito.any(Register::class.java))).thenReturn(registerMock)
    val response = registerService.save(registerMock)
    Assertions.assertEquals(response.id, registerMock.id)
  }

  @Test
  fun saveRegisterSecWhenIsCorrect(){
    Mockito.`when`(conferenceRepository.findById(registerMock.conferenceId)).thenReturn(conferenceMock)
    Mockito.`when`(registerRepository.save(Mockito.any(Register::class.java))).thenReturn(registerMock)
    val response = registerService.save(registerMock)
    Assertions.assertEquals(response.id, registerMock.id)
  }
}
