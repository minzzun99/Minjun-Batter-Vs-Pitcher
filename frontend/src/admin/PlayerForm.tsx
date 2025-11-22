import { useState, useEffect } from "react";
import "../styles/PlayerForm.css";

interface PlayerFormProps {
  onSubmit: (playerData: any) => Promise<boolean>;
  onCancel: () => void;
  initialData?: any;
}

export default function PlayerForm({
  onSubmit,
  onCancel,
  initialData,
}: PlayerFormProps) {
  const [formData, setFormData] = useState({
    name: "",
    totalAtBats: 0,
    singles: 0,
    doubles: 0,
    triples: 0,
    homeRuns: 0,
  });

  useEffect(() => {
    if (initialData) {
      setFormData({
        name: initialData.name,
        totalAtBats: initialData.totalAtBats,
        singles: initialData.singles,
        doubles: initialData.doubles,
        triples: initialData.triples,
        homeRuns: initialData.homeRuns,
      });
    } else {
      setFormData({
        name: "",
        totalAtBats: 0,
        singles: 0,
        doubles: 0,
        triples: 0,
        homeRuns: 0,
      });
    }
  }, [initialData]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: name === "name" ? value : Number(value) || 0,
    }));
  };

  const totalHits =
    formData.singles + formData.doubles + formData.triples + formData.homeRuns;

  const battingAverage =
    formData.totalAtBats > 0
      ? (totalHits / formData.totalAtBats).toFixed(3)
      : "0.000";

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (totalHits > formData.totalAtBats) {
      alert("안타 합계가 총 타수를 초과할 수 없습니다!");
      return;
    }

    const success = await onSubmit({
      ...formData,
      battingAverage: Number(battingAverage),
    });

    // 추가일 때만 reset
    if (success && !initialData) {
      setFormData({
        name: "",
        totalAtBats: 0,
        singles: 0,
        doubles: 0,
        triples: 0,
        homeRuns: 0,
      });
    }
  };

  return (
    <form onSubmit={handleSubmit} className="playerform-card">
      <h2 className="playerform-title">
        {initialData ? "선수 정보 수정" : "선수 추가"}
      </h2>

      {/* 이름 */}
      <div>
        <label className="playerform-label">선수 이름</label>
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          required
          className="playerform-input"
        />
      </div>

      {/* 총 타수 + 타율 */}
      <div className="grid grid-cols-2 gap-8">
        <div>
          <label className="playerform-label">총 타수</label>
          <input
            type="number"
            name="totalAtBats"
            value={formData.totalAtBats}
            onChange={handleChange}
            min="1"
            className="playerform-input"
          />
        </div>

        <div>
          <label className="playerform-label">
            예상 타율: <span className="text-blue-400">{battingAverage}</span>
          </label>
          <p className="playerform-label">
            안타합계 {totalHits} / {formData.totalAtBats}
          </p>
        </div>
      </div>

      {/* 안타 입력 */}
      <div className="grid grid-cols-2 gap-8">
        {["singles", "doubles", "triples", "homeRuns"].map((field) => (
          <div key={field}>
            <label className="playerform-label">
              {
                {
                  singles: "1루타",
                  doubles: "2루타",
                  triples: "3루타",
                  homeRuns: "홈런",
                }[field]
              }
            </label>
            <input
              type="number"
              name={field}
              value={formData[field as keyof typeof formData]}
              onChange={handleChange}
              min="0"
              className="playerform-input"
            />
          </div>
        ))}
      </div>

      {/* 경고 */}
      {totalHits > formData.totalAtBats && (
        <div className="playerform-warning">
          ⚠ 안타 합계가 총 타수를 초과합니다!
        </div>
      )}

      {/* 버튼 */}
      <div className="playerform-buttons">
        <button
          type="submit"
          disabled={totalHits > formData.totalAtBats}
          className="playerform-btn playerform-btn-primary"
        >
          {initialData ? "수정" : "추가"}
        </button>
        <button
          type="button"
          className="playerform-btn playerform-btn-gray"
          onClick={onCancel}
        >
          취소
        </button>
      </div>
    </form>
  );
}
