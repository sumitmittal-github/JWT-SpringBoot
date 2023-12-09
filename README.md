# Spring Boot 3.0 with JWT

<p>
	This repository contains Spring boot 3.0 with JWT
</p>



<p> 
	REST Clients to test the Microservices are - <br/>
	1. Add chrome extension :	ReqBin HTTP Client (https://reqbin.com/)  <br/>
	2. Add chrome extension :	Talend API Tester - Free Edition <br/>
</p>



<p>
	To download 256 bit Sign in key -  <br/>
	1. https://seanwasere.com/generate-random-hex/ <br/>
	2. https://asecuritysite.com/encryption/plain <br/>
	3. https://www.save-editor.com/crypto/crypt_key_generator.html <br/>
	3. http://allkeysgenerator.com <br/>
</p>



<p>
	verify this JWT Token from - 	https://jwt.io/
</p>



<p>
	API-1 : To register the user in DB -
	POST : http://localhost:8080/api/v1/auth/register

	Body : 
	{
	  "firstname": "Sumit",
	  "lastname": "Mittal",
	  "email": "sumit@gmail.com",
	  "username": "sumit",
	  "password": "password"
	}

	Response : 
	"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdW1pdCIsImlhdCI6MTcwMjEzNTM0OCwiZXhwIjoxNzAyMTM3MTQ4fQ.ebIB3wZdU_l1J8WzhlsZ-BLdIvelfJKbgtrCXuL8Dw0"
</p>



<p>
	API-2 : To authenticate the user
	POST : http://localhost:8080/api/v1/auth/authenticate
	{
	  "username": "sumit",
	  "password": "password"
	}

	Response : 
	"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdW1pdCIsImlhdCI6MTcwMjEzNTM0OCwiZXhwIjoxNzAyMTM3MTQ4fQ.ebIB3wZdU_l1J8WzhlsZ-BLdIvelfJKbgtrCXuL8Dw0"
</p>



<p>
	API-3 : To access secure API
	GET : http://localhost:8080/api/v1/user/hello
	Authorization -> Bearer token : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdW1pdCIsImlhdCI6MTcwMjEzNTM0OCwiZXhwIjoxNzAyMTM3MTQ4fQ.ebIB3wZdU_l1J8WzhlsZ-BLdIvelfJKbgtrCXuL8Dw0


	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdW1pdCIsImlhdCI6MTcwMjEzNTQ3MSwiZXhwIjoxNzAyMTM3MjcxfQ.TR9ZcMxXrS3nVVEdpvJG7RHvqMxb_dO40puVOfgQmJQ
</p>

