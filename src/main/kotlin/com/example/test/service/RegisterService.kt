package com.example.test.service


import com.example.test.model.Register
import com.example.test.repository.ConferenceRepository
import com.example.test.repository.MemberRepository
import com.example.test.repository.RegisterRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import kotlin.random.Random

@Service
class RegisterService {
  @Autowired
  lateinit var registerRepository: RegisterRepository
  @Autowired
  lateinit var memberRepository: MemberRepository
  @Autowired
  lateinit var conferenceRepository:ConferenceRepository



  fun list(pageable: Pageable, register: Register): Page<Register> {
    val matcher = ExampleMatcher.matching()
      .withIgnoreNullValues()
      .withMatcher(("registeredAt"), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
    return registerRepository.findAll(Example.of(register, matcher), pageable)
  }

  fun listById(id:Long?):Register?{
    return registerRepository.findById(id)
  }

    fun listConferences(mid: Long?):Any? {
    return registerRepository.allConferences(mid)
  }
  fun delete(id: Long?):Boolean?{
    registerRepository.findById(id)?:
    throw Exception()
    registerRepository.deleteById(id!!)
    return true
  }
  fun save(register: Register): Register {
    try {
      val response = registerRepository.save(register)
      return response
    }catch (ex:Exception){
      throw ResponseStatusException(
        HttpStatus.NOT_FOUND, ex.message, ex
      )
    }
  }

  fun update(register: Register): Register {
    try {
      registerRepository.findById(register.id)
        ?: throw Exception("El id ${register.id} en registere no existe")
      return registerRepository.save(register)
    } catch (ex: Exception) {
      throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
    }
  }

  fun updateCode (register: Register){
    val arr = IntArray(5) { Random.nextInt(1,9) }
    val gencode = arr.joinToString ("-")
    val registerResponse = registerRepository.findById(register.id)
    registerResponse?.apply{
      code = gencode
    }
    registerRepository.save(registerResponse!!)
  }

  fun updateAssisted(register: Register): Register {
    try {
      val response = registerRepository.findById(register.id)
        ?: throw Exception("Id existente :v")
      response.apply {
        assisted= register.assisted
      }
      return registerRepository.save(response)
    } catch (ex: Exception) {
      throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
    }
  }

  fun countAssisted(register: Register){
    val sumAssisted = registerRepository.sumAssisted(register.conferenceId)
    val conferenceResponse = conferenceRepository.findById(register.conferenceId)
    conferenceResponse?.apply {
      totalAttendees = sumAssisted
    }
    conferenceRepository.save(conferenceResponse!!)
  }



}
