import {NativeStackNavigationProp} from '@react-navigation/native-stack';

// Route Parameter List
type RootStackParamList = {
  Home: undefined;
  Login: undefined;
  About: undefined;
  Survey: undefined;
  Test: undefined;
};

// Porps by Screen
type HomeScreenNavigationProps = NativeStackNavigationProp<
  RootStackParamList,
  'Home'
>;
type LoginScreenNavigationProps = NativeStackNavigationProp<
  RootStackParamList,
  'Login'
>;

type AboutScreenNavigationProps = NativeStackNavigationProp<
  RootStackParamList,
  'About'
>;
type SurveyScreenNavigationProps = NativeStackNavigationProp<
  RootStackParamList,
  'Survey'
>;
type TestScreenNavigationProps = NativeStackNavigationProp<
  RootStackParamList,
  'Test'
>;

export type {
  RootStackParamList,
  HomeScreenNavigationProps,
  LoginScreenNavigationProps,
  AboutScreenNavigationProps,
  SurveyScreenNavigationProps,
  TestScreenNavigationProps,
};