const getConnectionType = () => {
  const networkState = navigator.connection.type;
  let states = {};
  states[Connection.UNKNOWN] = "Desconocida";
  states[Connection.ETHERNET] = "Ethernet";
  states[Connection.WIFI] = "WiFi";
  states[Connection.CELL_2G] = "2G";
  states[Connection.CELL_3G] = "3G";
  states[Connection.CELL_4G] = "4G";
  states[Connection.CELL] = "Genérica";
  states[Connection.NONE] = "Ninguna";
  return states[networkState];
};

const createComponent = (data) => {
  return `<p style="text-align: center">Tipo de red: ${data}</p>`;
};

const appendList = (object, data) => {
  object.insertAdjacentHTML("afterbegin", createComponent(data));
};

document.addEventListener("deviceready", function (e) {
  appendList(document.getElementById("info-wrapper"), getConnectionType());
});
