import React, { useState } from "react";
import {
  View,
  ScrollView,
  StatusBar,
  Switch,
  TextInput,
  Text,
  Button,
} from "react-native";
import styles from "./styles";
import Greeting from "./Greeting";
import Terms from "./Terms";
import Form from "./Form";

const inputsAreEmpty = (email, password) => {
  email === "" || password === ""
    ? alert("Debes llenar todos los campos")
    : alert("Bienvenido: " + email);
};

const App = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [termsCheck, setTermsCheck] = useState(false);

  return (
    <View style={styles.container}>
      <Greeting />
      <Form setEmail={(e) => setEmail(e)} setPassword={(p) => setPassword(p)} />
      <Terms setTermsCheck={(e) => setTermsCheck(e)} />
      <View style={{ marginTop: 35 }}>
        <Button
          disabled={!termsCheck}
          onPress={() => inputsAreEmpty(email, password)}
          style={styles.confirmBtn}
          title="Crear cuenta"
        />
      </View>
    </View>
  );
};

export default App;
