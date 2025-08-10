import express from 'express';
import path from 'path';
import { fileURLToPath } from 'url';

const app = express();
const port = 3000;

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(express.static(path.join(__dirname, 'public')));

app.get('/', async (req, res) => {
  res.render('index.ejs');
});

app.get('/set-password', async (req, res) => {
  const apiUrl = process.env.API_URL;
  res.render('password/setPassword.ejs', { apiUrl });
});

app.get('/set-password-success', async (req, res) => {
  res.render('password/setPasswordSuccess.ejs');
});

app.listen(port, () => {
  console.log(`Server listening on http://localhost:${port}`);
});