import { Observable } from "@nativescript/core";
import { CheckBox } from "@nstudio/nativescript-checkbox";
import { Frame } from "@nativescript/core";

function getCheckProp() {
  const checkBoxValue = Frame.topmost().getViewById("allow-negatives");
  return checkBoxValue.checked;
}

function isNumeric(n) {
  if (n === null || n === undefined || typeof n != "string") {
    return false;
  }
  return !isNaN(parseInt(n)) && isFinite(n);
}

export function createViewModel() {
  const viewModel = new Observable();
  viewModel.counter = 0;
  viewModel.message = viewModel.counter;

  viewModel.increment = () => {
    const check = getCheckProp();
    if (!check && viewModel.counter < 0) {
      viewModel.counter = 0;
      viewModel.counter++;
      viewModel.set("message", viewModel.counter);
      return;
    }

    viewModel.counter++;
    viewModel.set("message", viewModel.counter);
  };

  viewModel.decrement = () => {
    const check = getCheckProp();
    if (!check && viewModel.counter > 0) {
      viewModel.counter--;
      viewModel.set("message", viewModel.counter);
    }

    if (check) {
      viewModel.counter--;
      viewModel.set("message", viewModel.counter);
    }

    if (!check && viewModel.counter < 0) {
      viewModel.counter = 0;
      viewModel.set("message", viewModel.counter);
      return;
    }
  };

  viewModel.reset = (btargs) => {
    let btn = btargs.object;
    let resetValue = btn.page.getViewById("reset-value").text;

    if (!isNumeric(resetValue)) {
      alert("Introduce un número válido");
      return;
    }

    const check = getCheckProp();

    if (!check && resetValue < 0) {
      alert("No se admiten números negativos sin pulsar sobre el check.");
      return;
    }

    viewModel.counter = parseInt(resetValue);
    viewModel.set("message", viewModel.counter);
  };
  return viewModel;
}
