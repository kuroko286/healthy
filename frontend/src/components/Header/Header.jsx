import { useSelector } from 'react-redux';
import { selectIsLoggedIn } from '../../redux/selectors';
import { HeaderAuth } from './HeaderVariables/HeaderAuth';
import { HeaderNotAuth } from './HeaderVariables/HeaderNotAuth';

export const Header = () => {
  const isLogedin = useSelector(selectIsLoggedIn);
  return <>{isLogedin ? <HeaderAuth /> : <HeaderNotAuth />}</>;
};
