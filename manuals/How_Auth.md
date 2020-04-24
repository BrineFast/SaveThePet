## Auth (Google, Facebook, Yandex, Basic)
Now you can authenticate in two ways: using a username and password, or via oAuth.
### Authentication via login and password:
You can use any password and login, but first specify them in `src/main/resources/application.properties` in lines
```
admin.account.username=admin
admin.account.password=password
```
#### Let's pass to authorization:
1) Prepare a `POST` HTTP request to the endpoint `http://localhost:8080/login
2) Add form-data body with keys username and password with with  a username and password respectively
3) Make the request and you will see the following raw HTTP request:
```
POST /login HTTP/1.1
Host: localhost:8080
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="username"
username
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="password"
password
----WebKitFormBoundary7MA4YWxkTrZu0gW

```
4) Unfortunately response code will be 200 anyway. It will be fixed in next update
### Authentication via oAuth

Go to endpoint  `/oauth2/authorization/code`, where `code` this is "google","yadnex" or "facebook" then enter the data from your account.
If authorization is successful, you will be redirected to endpoit `/home` and your data will be stored in the database in the `users` table, you can check it by SQL request.

#### Note
For Facebook oAuth u need developer Facebook account and approvment in our app. If u need this - write me in vk(https://vk.com/d__e___d).
