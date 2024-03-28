import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { Navigate, useNavigate } from 'react-router-dom';
import { getCookieByName } from '../../utils/Cookie';
import { updateUserId } from '../../redux/Slices/auth';

function Oauth2Redirect() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const userId = getCookieByName('user_id');
  useEffect(() => {
    dispatch(updateUserId(userId));
    navigate('/main');
  }, [userId, dispatch, navigate]);
  return <Navigate to="/main" />;
}

export default Oauth2Redirect;
