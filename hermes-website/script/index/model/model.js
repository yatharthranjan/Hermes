var model = {
	pageCall: function (data) {
		view.loader();



		var xhr = new XMLHttpRequest();
		//var loginData="loginData="+data;

		xhr.open("POST", "php_files/" + data + ".php", true);
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		xhr.send(null);
		xhr.onreadystatechange = function () {
			if (xhr.readyState === 4) {
				if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
					var response = xhr.responseText;

					document.getElementById("main_div").innerHTML = response;

					if (data === "login_page") {
						document.getElementById("login_submit").onclick = controller.login;

					} else if (data === "signup_page") {
						document.getElementById("signup_submit").onclick = controller.signupValidation;
					}

				} else {
					document.getElementById("loader").innerHTML = "<h2 class=\"warning\">Failed To load Data </h2>";

				}



			}


		};



	},
	callAjax: function (data) {

		view.loader();

		//alert(data);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "php_files/validation.php", true);
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		xhr.send(data);
		xhr.onreadystatechange = function () {
			if (xhr.readyState === 4) {
				if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
					var response = xhr.responseText;
					console.log(response);

					if (response === "login_success") {
						console.log(response);

						window.location = "php_files/home.php";
					} else if (response === "signup_success") {

						document.getElementById("loader").innerHTML = "<h2 class=\"success\">Signup Success Click to Login</h2>";

					} else {
						//console.log(response);

						document.getElementById("loader").innerHTML = "<h2 class=\"warning\">Filed to Proceed</h2>";

					}



				}


			}



		};
	}
};
