package com.cabeleireiro.resources.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cabeleireiro.services.exceptions.DatabaseException;
import com.cabeleireiro.services.exceptions.EmailException;
import com.cabeleireiro.services.exceptions.ForbiddenException;
import com.cabeleireiro.services.exceptions.ReservaNotFoundException;
import com.cabeleireiro.services.exceptions.ResourceNotFoundException;
import com.cabeleireiro.services.exceptions.UnauthorizedException;

@ControllerAdvice
public class ResourceExceptionHandler {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND; // 404
		StandardError err = new StandardError();
		err.setTimestamp(LocalDateTime.now().format(formatter));
		err.setStatus(status.value());
		err.setError("Recurso não encontrado");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(ReservaNotFoundException.class)
	public ResponseEntity<StandardError> reservaNotFound(ReservaNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND; // 404
		StandardError err = new StandardError();
		err.setTimestamp(LocalDateTime.now().format(formatter));
		err.setStatus(status.value());
		err.setError("Erro na atualização");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST; //400
		StandardError err = new StandardError();
		err.setTimestamp(LocalDateTime.now().format(formatter));
		err.setStatus(status.value());
		err.setError("Exceção no banco");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<StandardError> email(EmailException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST; //400
		StandardError err = new StandardError();
		err.setTimestamp(LocalDateTime.now().format(formatter));
		err.setStatus(status.value());
		err.setError("Exceção no envio de e-mail");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validacao(MethodArgumentNotValidException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; //422 -> serve para validação de formulários
		ValidationError err = new ValidationError();
		err.setTimestamp(LocalDateTime.now().format(formatter));
		err.setStatus(status.value());
		err.setError("Exceção na validação");
		err.setMessage(String.valueOf(e.getFieldError()));
		err.setPath(request.getRequestURI());
		
		// pra pegar a lista de erros da validação
		for(FieldError errors : e.getBindingResult().getFieldErrors()) { 
			err.addError(errors.getField(), errors.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<OAuthCustomError> forbidden(ForbiddenException e, HttpServletRequest request){
		OAuthCustomError err = new OAuthCustomError("Não autorizado", e.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<OAuthCustomError> unauthorized(UnauthorizedException e, HttpServletRequest request){
		OAuthCustomError err = new OAuthCustomError("Não autenticado", e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}
}