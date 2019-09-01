package pl.sda.spring.service;

import org.springframework.stereotype.Service;
import pl.sda.spring.model.Donation;
import pl.sda.spring.model.Whipround;
import pl.sda.spring.model.WhiproundSum;
import pl.sda.spring.repository.DonationRepository;
import pl.sda.spring.repository.WhiproundRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WhiproundService {

    private final WhiproundRepository whiproundRepository;
    private final DonationRepository donationRepository;

    public WhiproundService(WhiproundRepository whiproundRepository, DonationRepository donationRepository) {
        this.whiproundRepository = whiproundRepository;
        this.donationRepository = donationRepository;
    }

    public List<WhiproundSum> getWhiproundList(){
        List<Whipround> allWhiprounds = whiproundRepository.findAll();
        List<WhiproundSum> whiproundsWithNotAchievedGoal = new ArrayList<>();
        allWhiprounds.forEach(whipround -> {
            List<Donation> byWhiproundId = donationRepository.findByWhiproundId(whipround.getId());
            BigDecimal sum = calculateSum(byWhiproundId);
            if (sum.compareTo(whipround.getGoal()) < 0) {
                whiproundsWithNotAchievedGoal.add(new WhiproundSum(whipround, sum));
            }
        });
        return whiproundsWithNotAchievedGoal;
    }

    private BigDecimal calculateSum(List<Donation> donations) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Donation donation : donations) {
            sum = sum.add(donation.getAmount());
        }
        return sum;
    }

    public void addDonation(Donation donation) {
        donationRepository.save(donation);
    }

    public Optional<Whipround> findById(Long id) {
        return whiproundRepository.findById(id);
    }
}