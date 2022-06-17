document.addEventListener("deviceready", function () {
  const deviceData = {
    dispositivo: device.platform,
    version: device.version,
    uuid: device.uuid,
    modelo: device.model,
    fabricante: device.manufacturer,
    cordova: device.cordova,
  };

  for (const key in deviceData) {
    document
      .getElementById("info-wrapper")
      .insertAdjacentHTML(
        "afterbegin",
        `<p align="center">${key}: ${deviceData[key]}<p>`
      );
  }
});
