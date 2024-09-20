import React from 'react';
import { View, StyleSheet, ImageBackground } from 'react-native';
import TopBar from './TopBar';
import GeoMapperTitle from './GeoMapperTitle';
import Button from './Button';

const LandingPage = () => {
  return (
    <View style={styles.container}>
      <ImageBackground
        source={require('../assets/Globe.png')} // Globe image
        style={styles.globe}
      >
        <TopBar />
        <View style={styles.logoContainer}>
          <ImageBackground
            source={require('../assets/FIU.png')} // Logo
            style={styles.logo}
          />
        </View>
        <GeoMapperTitle />
        <View style={styles.buttonRow}>
          <Button label="Create New Project" />
          <Button label="Open Existing Project" />
        </View>
      </ImageBackground>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFFFFF',
  },
  globe: {
    flex: 1,
    resizeMode: 'cover',
  },
  logoContainer: {
    alignItems: 'center', // Center the logo horizontally
    marginTop: 100, // Adjust to position the logo near the top of the screen
    marginBottom: 20, // Space between the logo and the title
  },
  logo: {
    width: 268.25,
    height: 125.89,
  },
  buttonRow: {
    flexDirection: 'row', 
    justifyContent: 'center', 
    alignItems: 'center',
    marginTop: 200,
  }
});

export default LandingPage;
