import { test } from "@playwright/test";

test("open page", async ({ page }) => {
  const url = 'https://qastaging.siigo.com/#/login'; 
  await page.goto(url);

  //Datos para iniciar sesion
  const username = 'admin@siigoautomationqa.com';
  const password = '$22g0QA321';
  await page.goto(url);
  await page.fill('#username-input', username);
  await page.fill('#password-input', password);
  await page.click("#login-submit");
  const testoEsperado='Inicio';
  const contenidoPagina = await page.content();
  if (contenidoPagina.includes(testoEsperado)) {
    console.log(`El texto "${testoEsperado}" está presente en la página.`);
  } else {
    console.log(`El texto "${testoEsperado}" no está presente en la página.`);
  }
 
  //valiar opcion crear
  await page.getByLabel('click here').click();
  await page.goto('https://qastaging.siigo.com/#/dashboard/1055');
  await page.getByRole('button', { name: 'Crear' }).click();
  const visible = await page.getByText("Proceso de ventas").isVisible();
  console.log(visible);
  await page.waitForTimeout(1000);


  

});
