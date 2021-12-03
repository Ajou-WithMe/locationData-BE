package ajou.withme.locationData.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ajou.withme.locationData.repository.PredictionLocationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PredictionLocationService {

	private final PredictionLocationRepository predictionLocationRepository;

	public List<Object[]> findAllLocationByUserId(Long id) {

		return predictionLocationRepository.findAllById(id);

	}
}
