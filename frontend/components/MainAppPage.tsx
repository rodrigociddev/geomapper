// MainAppPage.tsx
import React from 'react';
import { View, StyleSheet } from 'react-native';
import TopBarGeoTagR from './TopBarGeoTagR'; // Top bar for GeoTagR
import SidePanel from './SidePanel'; // Side panel component
console.log(TopBarGeoTagR); // Should not be undefined
console.log(SidePanel); // Should not be undefined
const MainAppPage = () => {
  return (
    <View style={styles.container}>
      <TopBarGeoTagR />
      <View style={styles.body}>
        <SidePanel />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  body: {
    flexDirection: 'row',
    flex: 1,
  },
});

export default MainAppPage;
