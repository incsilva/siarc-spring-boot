/**
 * 
 */

function conferePassword() {
	const password = document.querySelector('input[name=password]');
	const confirmPassword = document
		.querySelector('input[name=confirmPassword]');

	if (confirmPassword.value === password.value) {
		confirmPassword.setCustomValidity('');
	} else {
		confirmPassword.setCustomValidity('As senhas não conferem.');
	}
}

function passwordOk() {
	alert("Senhas compatíveis!")
}


function verificaForcaSenha() {
	var numeros = /([0-9])/;
	var alfabeto = /([a-zA-Z])/;
	var chEspeciais = /([~,!,@,#,$,%,^,&,*,-,_,+,=,?,>,<])/;

	if ($('#password').val().length < 6) {
		$('#password-status').html("<span style='color:red'>Fraco, insira no mínimo 8 caracteres</span>");
	} else {
		if ($('#password').val().match(numeros) && $('#password').val().match(alfabeto) && $('#password').val().match(chEspeciais)) {
			$('#password-status').html("<span style='color:green'><b>Forte</b></span>");
		} else {
			$('#password-status').html("<span style='color:orange'>Médio, é necessário conter ao menos um número, uma letra e um símbolo para criar uma senha forte.)</span>");
		}
	}
}