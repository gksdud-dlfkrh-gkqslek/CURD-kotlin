package com.bs.crudkotlin.Service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class SupabaseStorageService(
    @Value("\${supabase.url}") private val supabaseUrl: String,
    @Value("\${supabase.key}") private val supabaseKey: String,
    @Value("\${supabase.bucket}") private val bucket: String
) {
    private val restTemplate = RestTemplate()

    fun upload(file: MultipartFile): String {
        val fileName = "${UUID.randomUUID()}_${file.originalFilename}"

        val headers = HttpHeaders().apply {
            set("Authorization", "Bearer $supabaseKey")
            set("apikey", supabaseKey)
            contentType = MediaType.parseMediaType(file.contentType ?: "application/octet-stream")
        }

        val request = HttpEntity(file.bytes, headers)

        val url = "$supabaseUrl/storage/v1/object/$bucket/$fileName"

        restTemplate.exchange(url, HttpMethod.POST, request, String::class.java)

        return fileName
    }

    fun delete(fileName: String) {
        val headers = HttpHeaders().apply {
            set("Authorization", "Bearer $supabaseKey")
            set("apikey", supabaseKey)
            contentType = MediaType.APPLICATION_JSON
        }

        val body = """{"prefixes":["$fileName"]}"""
        val request = HttpEntity(body, headers)

        val url = "$supabaseUrl/storage/v1/object/$bucket"

        try {
            restTemplate.exchange(url, HttpMethod.DELETE, request, String::class.java)
        } catch (_: Exception) {}
    }

    fun getPublicUrl(fileName: String): String {
        return "$supabaseUrl/storage/v1/object/public/$bucket/$fileName"
    }
}
