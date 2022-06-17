import React from 'react';
import {View, Image, Text} from 'react-native';
import styles from './styles';

const Greeting = () => {
  return (
    <View style={styles.greetContainer}>
      <Text style={styles.greet}>Bienvenido a Práctica3 React Native</Text>
      <Image
        style={styles.tinyLogo}
        source={{
          uri: 'https://reactnative.dev/img/tiny_logo.png',
        }}
      />
    </View>
  );
};

export default Greeting;
