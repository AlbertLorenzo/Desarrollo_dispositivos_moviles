import React from 'react';
import {View, Text, TextInput} from 'react-native';
import styles from './styles';

const Form = ({setEmail, setPassword}) => {
  return (
    <View styles={styles.container}>
      <Text style={styles.inputLabel}>Email</Text>
      <TextInput
        placeholder="Email"
        onChangeText={email => setEmail(email)}
        style={styles.input}
      />
      <Text style={styles.inputLabel}>Password</Text>
      <TextInput
        secureTextEntry={true}
        placeholder="Password"
        onChangeText={password => setPassword(password)}
        style={styles.input}
      />
    </View>
  );
};

export default Form;
