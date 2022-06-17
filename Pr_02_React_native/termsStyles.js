import {StyleSheet} from 'react-native';

const termsStyles = StyleSheet.create({
  sw: {
    marginTop: 20,
    marginLeft: 20,
    marginRight: 20,
    marginBottom: 20,
    alignSelf: 'center',
    width: '15%',
    height: 50,
    borderRadius: 10,
    backgroundColor: '#f4f3f4',
    borderColor: '#f4f3f4',
    borderWidth: 1,
    borderStyle: 'solid',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 5,
  },
  container: {
    marginTop: 20,
    maxHeight: '30%',
  },
  scrollView: {
    flexGrow: 0,
  },
  text: {
    fontSize: 15,
  },
});

export default termsStyles;
