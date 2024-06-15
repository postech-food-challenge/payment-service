package br.com.fiap.postech.domain.exceptions

import java.lang.Exception

class ClientException(msg: String, e: Exception): RuntimeException(msg, e)