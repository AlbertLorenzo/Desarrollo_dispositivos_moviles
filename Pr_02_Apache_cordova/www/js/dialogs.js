const alertDialogFnt = function () {
  navigator.notification.alert("Alerta");
};

const confirmDialogFnt = function () {
  navigator.notification.confirm("Confirm");
};

const promptDialogFnt = function () {
  navigator.notification.prompt("Prompt");
};

document.addEventListener("deviceready", function () {
  const items = {
    alertDialog: alertDialogFnt,
    confirmDialog: confirmDialogFnt,
    promptDialog: promptDialogFnt,
  };

  for (const key in items) {
    document.getElementById(`${key}`).addEventListener("click", function (e) {
      e.preventDefault();
      items[key]();
    });
  }
});
