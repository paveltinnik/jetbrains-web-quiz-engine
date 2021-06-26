# jetbrains-web-quiz-engine
Spring приложение для создания викторин, а также их решения. Использованы Spring Boot, Spring Security, Hibernate

## Регистрация пользователя
To register a new user, the client needs to send a JSON with email and password via `POST` request to `/api/register`:
```json
{
  "email": "test@gmail.com",
  "password": "secret"
}
```
The service returns `200 (OK)` status code if the registration has been completed successfully.

If the email is already taken by another user, the service will return the `400 (Bad request)` status code.

Here are some additional restrictions to the format of user credentials:
- the email must have a valid format (with `@` and `.`);
- the password must have at least five characters.
If any of them is not satisfied, the service will also return the `400 (Bad request)` status code.
All the following operations need a registered user to be successfully completed.
## Delete a quiz
A user can delete their quiz by sending the `DELETE` request to `/api/quizzes/{id}`.
If the operation was successful, the service returns the `204 (No content)` status code without any content.
If the specified quiz does not exist, the server returns `404 (Not found)`. If the specified user is not the author of this quiz, the response is the `403 (Forbidden)` status code.
## Create a new quiz
To create a new quiz, the client needs to send a JSON as the request's body via POST to /api/quizzes. The JSON should contain the four fields:

- title: a string, required;
- text: a string, required;
- options: an array of strings, required, should contain at least 2 items;
- answer: an array of indexes of correct options, optional, since all options can be wrong.
Here is a new JSON quiz as an example:
```json
{
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"],
  "answer": [0,2]
}
```
The `answer` equals `[0,2]` corresponds to the first and the third item from the options array (`"Americano"` and `"Cappuccino"`).
The server response is a JSON with four fields: `id`, `title`, `text` and `options`. Here is an example:
```json
{
  "id": 1,
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"]
}
```
The `id` field is a generated unique integer identifier for the quiz.
If the request JSON does not contain title or text, or they are empty strings (""), then the server respond with the `400 (Bad request)` status code. If the number of options in the quiz is less than 2, the server returns the same status code.
