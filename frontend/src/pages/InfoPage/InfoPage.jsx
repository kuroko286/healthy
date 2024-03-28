import { useSelector } from 'react-redux';
import { selectIsLoggedIn } from '../../redux/selectors';
import { Navigate } from 'react-router-dom';

function InfoPage() {
  const isLoggedIn = useSelector(selectIsLoggedIn);
  if (!isLoggedIn) {
    return <Navigate to="/welcome" />;
  }
  return <div>InfoPage</div>;
}

export default InfoPage;
