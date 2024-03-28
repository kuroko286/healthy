package com.kuroko.heathyapi.feature.user.service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kuroko.heathyapi.components.Nutrition;
import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.food.FoodRepository;
import com.kuroko.heathyapi.feature.food.dto.CaloriesPD;
import com.kuroko.heathyapi.feature.meal.MealRepository;
import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.user.UserRepository;
import com.kuroko.heathyapi.feature.user.model.Gender;
import com.kuroko.heathyapi.feature.user.model.Goal;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.user.payload.GoalRequest;
import com.kuroko.heathyapi.feature.user.payload.GoalResponse;
import com.kuroko.heathyapi.feature.user.payload.StatisticsResponse;
import com.kuroko.heathyapi.feature.user.payload.UserRequest;
import com.kuroko.heathyapi.feature.user.payload.UserResponse;
import com.kuroko.heathyapi.feature.water.Water;
import com.kuroko.heathyapi.feature.water.WaterRepository;
import com.kuroko.heathyapi.feature.water.dto.WaterPD;
import com.kuroko.heathyapi.feature.weight.WeightRepository;
import com.kuroko.heathyapi.feature.weight.dto.WeightPD;
import com.kuroko.heathyapi.service.FileUploadService;

@Service
public class UserServiceImpl implements UserService {
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private AccountRepository accountRepository;
        @Autowired
        private FileUploadService fileUploadService;
        @Autowired
        private WaterRepository waterRepository;
        @Autowired
        private WeightRepository weightRepository;
        @Autowired
        private FoodRepository foodRepository;
        @Autowired
        private MealRepository mealRepository;

        @Override
        public StatisticsResponse getStatistics(int month, int year, Long id) {
                if (id == null) {
                        throw new InvalidParameterException("user id cannot be null");
                }
                User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                                "User with id " + id + " not found."));

                return getStatisticsMonth(user, month, year);
        }

        public StatisticsResponse getStatisticsMonth(User user, int month, int year) {
                List<Object[]> waterPerDay = waterRepository.findByYearAndMonthAndUser(year, month, user);
                List<WaterPD> waterPerDayList = waterPerDay.stream()
                                .map(water -> new WaterPD((int) water[0], (Double) water[1]))
                                .collect(Collectors.toList());

                List<Object[]> weightPerDay = weightRepository.findByYearAndMonthAndUser(year, month, user);
                List<WeightPD> weightPerDayList = weightPerDay.stream()
                                .map(weight -> new WeightPD((int) weight[0], (Double) weight[1]))
                                .collect(Collectors.toList());

                List<Object[]> callPerDay = foodRepository.findByYearAndMonthAndUser(year, month, user);
                List<CaloriesPD> callPerDayList = callPerDay.stream()
                                .map(call -> new CaloriesPD((int) call[0], (Double) call[1]))
                                .collect(Collectors.toList());
                StatisticsResponse statisticsDto = new StatisticsResponse(callPerDayList, waterPerDayList,
                                weightPerDayList);

                return statisticsDto;
        }

        @Override
        public UserResponse getCurrentUser(Long id) {
                if (id == null) {
                        throw new InvalidParameterException("user id cannot be null");
                }
                User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                                "User with id " + id + " not found."));
                List<Meal> meals = mealRepository.findByUserAndTimeRange(user, LocalDate.now().atStartOfDay(),
                                LocalDate.now().atStartOfDay().plusDays(1));
                List<Water> water = waterRepository.findByUserAndTimeRange(user, LocalDate.now().atStartOfDay(),
                                LocalDate.now().atStartOfDay().plusDays(1));
                return new UserResponse(user, meals, water);
        }

        @Override
        public UserResponse updateUserInfo(Long id, UserRequest userReq) {
                if (id == null) {
                        throw new InvalidParameterException("user id cannot be null");
                }
                User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                                "User with id " + id + " not found."));
                patchUser(user, userReq);
                User newUser = userRepository.save(user);
                System.out.println(newUser.toString());
                List<Meal> meals = mealRepository.findByUserAndTimeRange(newUser, LocalDate.now().atStartOfDay(),
                                LocalDate.now().atStartOfDay().plusDays(1));
                List<Water> water = waterRepository.findByUserAndTimeRange(newUser, LocalDate.now().atStartOfDay(),
                                LocalDate.now().atStartOfDay().plusDays(1));
                return new UserResponse(user, meals, water);

        }

        private void patchUser(User user, UserRequest userReq) {
                user.setName(userReq.getName());
                user.setAge(userReq.getAge());
                user.setGender(Gender.getGender(userReq.getGender()));
                user.setHeight(userReq.getHeight());
                user.setWeight(userReq.getWeight());
                user.setCoefficientOfActivity(userReq.getCoefficientOfActivity());
        }

        @Override
        public GoalResponse updateUserGoal(Long id, GoalRequest goal) {
                if (id == null) {
                        throw new InvalidParameterException("user id cannot be null");
                }
                User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                                "User with id " + id + " not found."));
                user.setGoal(Goal.getGoal(goal.getGoal()));
                userRepository.save(user);
                return new GoalResponse(goal.getGoal(), new Nutrition(user));
        }

        @Override
        public void updateUserAvatar(Long id, MultipartFile avatar) {
                if (id == null) {
                        throw new InvalidParameterException("user id cannot be null");
                }
                User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                                "User with id " + id + " not found."));
                String url = fileUploadService.uploadFile(avatar);
                user.setAvatarURL(url);
                userRepository.save(user);
        }

}
