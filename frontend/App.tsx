import React from 'react';
import { SafeAreaView, StatusBar, StyleSheet } from 'react-native';
import LandingPage from './components/LandingPage';
import TopBar from './components/TopBar';
import GeoMapperTitle from './components/GeoMapperTitle';
import Buttons from './components/Button';
import { MinusIcon, SquareIcon, XIcon } from './components/IconComponents';

const App = () => {
  return (
    <SafeAreaView style={styles.container}>
      <StatusBar barStyle="dark-content" />
      {/* The main LandingPage component that encapsulates everything */}
      <LandingPage />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFF',
  },
});

export default App;
