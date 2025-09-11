package com.fraudlabspro

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object Http {
  def get(url: URL): String = try {
    val conn = url.openConnection.asInstanceOf[HttpURLConnection]
    conn.setRequestMethod("GET")
    conn.setRequestProperty("Accept", "application/json")
    if (conn.getResponseCode != 200) return "Failed : HTTP error code : " + conn.getResponseCode
    val br = new BufferedReader(new InputStreamReader(conn.getInputStream))
    val resultFromHttp = new StringBuilder
    Iterator
      .continually(br.readLine())
      .takeWhile(_ != null)
      .foreach(line => resultFromHttp.append(line).append("\n"))
    br.close()
    conn.disconnect()
    resultFromHttp.toString
  } catch {
    case e: Exception =>
      throw new RuntimeException(e)
  }

  def post(url: URL, post: String): String = try {
    val conn = url.openConnection.asInstanceOf[HttpURLConnection]
    conn.setRequestMethod("POST")
    conn.setRequestProperty("Accept", "application/json")
    val urlParameters = post
    conn.setDoOutput(true)
    val dos = new DataOutputStream(conn.getOutputStream)
    dos.writeBytes(urlParameters)
    dos.flush()
    dos.close()
    if (conn.getResponseCode != 200) return "Failed : HTTP error code : " + conn.getResponseCode
    val br = new BufferedReader(new InputStreamReader(conn.getInputStream))
    val resultFromHttp = new StringBuilder
    Iterator
      .continually(br.readLine())
      .takeWhile(_ != null)
      .foreach(line => resultFromHttp.append(line).append("\n"))
    br.close()
    conn.disconnect()
    resultFromHttp.toString
  } catch {
    case e: Exception =>
      throw new RuntimeException(e)
  }
}