import { lazy, useEffect } from 'react';
import { Route, Routes } from 'react-router-dom';
import { Navigate } from 'react-router-dom';

import SharedLayout from './components/SharedLayout/SharedLayout';
const ErrorPage = lazy(() => import('./pages/ErrorPage/ErrorPage'));
const WelcomePage = lazy(() => import('./pages/WelcomePage/WelcomePage'));
const SignUpPage = lazy(() => import('./pages/SignUpPage/SignUpPage'));
const SignInPage = lazy(() => import('./pages/SignInPage/SignInPage'));
const ForgotPasswordPage = lazy(() =>
  import('./pages/ForgotPasswordPage/ForgotPasswordPage')
);

const MainPage = lazy(() => import('./pages/MainPage/MainPage'));
const DashboardPage = lazy(() => import('./pages/DashboardPage/DashboardPage'));
const DiaryPage = lazy(() => import('./pages/DiaryPage/DiaryPage'));
const RecommendedFoodPage = lazy(() =>
  import('./pages/RecommendedFoodPage/RecommendedFoodPage')
);
const SettingsPage = lazy(() => import('./pages/SettingsPage/SettingsPage'));
const ChatGptPage = lazy(() => import('./pages/ChatGptPage/ChatGptPage'));
const Oauth2Redirect = lazy(() => import('./pages/Oauth2Page/Oauth2Redirect'));
// const InfoPage = lazy(() => import('./pages/InfoPage/InfoPage'));

import { AppWrapper } from './App.styled';
import { useDispatch, useSelector } from 'react-redux';
import { getCurrentUser } from './redux/operations';
import { selectIsLoggedIn, selectUserId } from './redux/selectors';
// import { isUserInfoComplete } from './utils/user';

function PrivateRoute({ children }) {
  const isLoggedIn = useSelector(selectIsLoggedIn);
  return isLoggedIn ? <Navigate to="/main" /> : children;
}
function PublicRoute({ children }) {
  const isLoggedIn = useSelector(selectIsLoggedIn);
  return !isLoggedIn ? <Navigate to="/welcome" /> : children;
}

// function CompleteInfo({ children }) {
//   // check if user info is completed
//   const user = useSelector(selectUserId);
//   return isUserInfoComplete(user.info) ? children : <Navigate to="/info" />;
// }
function App() {
  const isLoggedIn = useSelector(selectIsLoggedIn);

  const dispatch = useDispatch();
  const userId = useSelector(selectUserId);

  useEffect(() => {
    if (userId) {
      dispatch(getCurrentUser(userId));
    }
  }, [dispatch, userId]);

  return (
    <AppWrapper>
      <Routes>
        <Route path="/" element={<SharedLayout />}>
          {isLoggedIn ? (
            <Route
              index
              element={
                <PublicRoute>
                  <MainPage />
                </PublicRoute>
              }
            />
          ) : (
            <Route
              index
              element={
                <PrivateRoute>
                  <WelcomePage />
                </PrivateRoute>
              }
            />
          )}

          {/* Nonauth */}
          <Route
            path="/welcome"
            element={
              <PrivateRoute>
                <WelcomePage />
              </PrivateRoute>
            }
          />
          <Route
            path="/signup"
            element={
              <PrivateRoute>
                <SignUpPage />
              </PrivateRoute>
            }
          />
          <Route
            path="/signin"
            element={
              <PrivateRoute>
                <SignInPage />
              </PrivateRoute>
            }
          />
          <Route
            path="/forgot-password"
            element={
              <PrivateRoute>
                <ForgotPasswordPage />
              </PrivateRoute>
            }
          />

          {/* Auth */}
          <Route
            path="/main"
            element={
              <PublicRoute>
                <MainPage />
              </PublicRoute>
            }
          />
          <Route
            path="/dashboard"
            element={
              <PublicRoute>
                <DashboardPage />
              </PublicRoute>
            }
          />
          <Route
            path="/diary"
            element={
              <PublicRoute>
                <DiaryPage />
              </PublicRoute>
            }
          />
          <Route
            path="/recommended-food"
            element={
              <PublicRoute>
                <RecommendedFoodPage />
              </PublicRoute>
            }
          />
          <Route
            path="/settings"
            element={
              <PublicRoute>
                <SettingsPage />
              </PublicRoute>
            }
          />
          <Route
            path="/chatgpt"
            element={
              <PublicRoute>
                <ChatGptPage />
              </PublicRoute>
            }
          />
          {/* <Route path="/info" element={<InfoPage />} /> */}
          <Route path="/oauth2/redirect" element={<Oauth2Redirect />} />
          <Route path="*" element={<ErrorPage />} />
        </Route>
      </Routes>
    </AppWrapper>
  );
}
export default App;
