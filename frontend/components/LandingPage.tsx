// LandingPage.tsx
import React from 'react';
import { View, StyleSheet, ImageBackground } from 'react-native';
import { useNavigation } from '@react-navigation/native'; // Import navigation hook
import TopBar from './TopBar';
import GeoMapperTitle from './GeoMapperTitle';
import Button from './Button';

const LandingPage = () => {
  const navigation = useNavigation(); // Get navigation instance

  return (
    <View style={styles.container}>
      <ImageBackground
        source={require('../assets/Globe.png')}
        style={styles.globe}
      >
        <TopBar />
        <View style={styles.logoContainer}>
          <ImageBackground
            source={require('../assets/FIU.png')}
            style={styles.logo}
          />
        </View>
        <GeoMapperTitle />
        <View style={styles.buttonRow}>
          <Button 
              label="Create New Project" 
              onPress={() => {
                console.log('Navigating to MainApp');
                navigation.navigate('MainApp');
              }}
          />
          <Button 
            label="Open Existing Project" 
            onPress={() => console.log('Open Existing Project')} 
          />
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
    alignItems: 'center', 
    marginTop: 100, 
    marginBottom: 20, 
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
