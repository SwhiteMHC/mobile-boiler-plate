// React
import React from 'react';

// Props

// Components

// RN Components
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  View,
  Text,
  Button,
} from 'react-native';

// Styles
import {styled} from 'nativewind';
const StyledView = styled(View);
const StyledScrollView = styled(ScrollView);
const StyledStatus = styled(StatusBar);

function LoginScreen({navigation}): JSX.Element {
  return (
    <SafeAreaView className="text-black dark:text-white">
      <StyledStatus className="text-black dark:text-white" />
      <StyledScrollView
        contentInsetAdjustmentBehavior="automatic"
        className="text-black dark:text-white bg-white/100">
        <StyledView className="text-black dark:text-white bg-white/100">
          <Text>Login</Text>
          <Button title="Home" onPress={() => navigation.navigate('Home')} />
          <Button title="About" onPress={() => navigation.navigate('About')} />
          <Button
            title="Survey"
            onPress={() => navigation.navigate('Survey')}
          />
          <Button
            title="Testing"
            onPress={() => navigation.navigate('Test')}
          />
        </StyledView>
      </StyledScrollView>
    </SafeAreaView>
  );
}

export default LoginScreen;
