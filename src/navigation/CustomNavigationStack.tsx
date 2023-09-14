import React from 'react';

// Screens
import HomeScreen from '../screens/Home/HomeScreen';
import AboutScreen from '../screens/About/AboutScreen';
import LoginScreen from '../screens/Login/LoginScreen';
import SurveyScreen from '../screens/Survey/SurveyScreen';
import TestScreen from '../screens/Testing/TestScreen';

// Navigation
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';

// Props
import {RootStackParamList} from './navigation.type';

// Navigation Stack
const Stack = createNativeStackNavigator<RootStackParamList>();

// Navigation Stack
function CustomNavigationStack() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen
          name="Home"
          component={HomeScreen}
          options={{title: 'Welcome'}}
        />
        <Stack.Screen name="About" component={AboutScreen} />
        <Stack.Screen name="Survey" component={SurveyScreen} />
        <Stack.Screen name="Test" component={TestScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

export {CustomNavigationStack};