// App.tsx
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import LandingPage from './components/LandingPage';
import MainAppPage from './components/MainAppPage'; // Import the new page

const Stack = createStackNavigator();

const App = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Landing">
        <Stack.Screen 
          name="Landing" 
          component={LandingPage} 
          options={{ headerShown: false }} // Hides the default header bar
        />
        {/* Main App Page */}
        <Stack.Screen 
          name="MainApp" 
          component={MainAppPage} 
          options={{ title: 'Main App', headerShown: false }} // Title of the new page
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
